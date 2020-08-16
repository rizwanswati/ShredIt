package com.devops.shredit;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

public class Security {

    protected static final int INTENT_AUTHENTICATE = 004;

    protected void AppAuthenticate(Context context, Activity activity) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            KeyguardManager km = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
            if (km.isKeyguardSecure()) {
                Intent authIntent = km.createConfirmDeviceCredentialIntent(context.getResources().getString(R.string.dialog_title_auth), context.getResources().getString(R.string.dialog_msg_auth));
                activity.startActivityForResult(authIntent, INTENT_AUTHENTICATE);
            }
        }
    }

    protected boolean AuthenticateReqResult(int resultCode) {
        if (resultCode == Activity.RESULT_OK) {
            return true;
        } else {
            return false;
        }
    }

}
