package com.rodrigoftw.myworkouttracker.myworkouttracker.component;

import android.content.Context;
import android.text.Html;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rodrigoftw.myworkouttracker.myworkouttracker.R;
import com.rodrigoftw.myworkouttracker.myworkouttracker.util.Util;

public class LoadLayout extends RelativeLayout {
    /**
     * Class attributes
     */
    private LayoutInflater mInflater;
    private Context context;
    public LoadLayout.Callback callback;

    /**
     * Construtores
     * @param context
     */
    public LoadLayout(Context context) {
        this(context, null);
    }

    public LoadLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        // save context
        this.context = context;

        // get inflater service
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * Verificar se há conexão de internet
     */
    private boolean checkNetwork() {
        if (!Util.isNetworkAvailable()) {
            setMessage("Sem conexão com a internet!<br>Por favor, tente novamente!", "{fa-wifi}", new OnClickListener() {
                @Override
                public void onClick(View v) {
                    reload();
                }
            });

            return false;
        }

        return true;
    }

    private void showChilds() {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            Log.e("asd", child.getClass().toString());
        }
    }

    public void removeMessagesView() {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child != null && child.getId() == R.id.containerMessageRefreshLayout) {
                ((ViewGroup) child.getParent()).removeView(child);
            }
        }
    }

    public void setMessage(final String message, final String icon, OnClickListener clickListener) {
        removeMessagesView();

        // create new
        View messageView = mInflater.inflate(R.layout.message_refresh_layout, null);
        LayoutParams params = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        messageView.setLayoutParams(params);

        ((TextView) messageView.findViewById(R.id.icon)).setText(icon);
        ((TextView) messageView.findViewById(R.id.message)).setText(Html.fromHtml(message));

        // add view
        addView(messageView, 0);

        if (clickListener != null) {
            // click listener
            messageView.setOnClickListener(clickListener);
            messageView.bringToFront();
            invalidate();
        } else {
            messageView.setClickable(false);
        }
    }

    public void reload() {
        // check internet
        if (!checkNetwork()) {
            if (callback != null) callback.fail();
            return;
        }

        removeMessagesView();
        if (callback != null) callback.reload();
    }

    public interface Callback {
        public void reload();
        public void fail();
    }
}
