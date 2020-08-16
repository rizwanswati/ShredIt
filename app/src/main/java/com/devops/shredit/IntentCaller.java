package com.devops.shredit;

import android.content.Context;
import android.content.Intent;

public class IntentCaller {

    protected void CallAbout(Context context, Class<?> go) {
        Intent intent = new Intent(context, go);
        context.startActivity(intent);
    }

    protected void CallHelp(Context context, Class<?> go) {
        Intent intent = new Intent(context, go);
        context.startActivity(intent);
    }

    protected void CallSupport(Context context, Class<?> go) {
        Intent intent = new Intent(context, go);
        context.startActivity(intent);
    }

    protected void CallHome(Context context, Class<?> go) {
        Intent intent = new Intent(context, go);
        context.startActivity(intent);
    }


}
