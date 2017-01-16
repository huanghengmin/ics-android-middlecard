/*
 * Copyright (c) 2012-2014 Arne Schwabe
 * Distributed under the GNU GPL v2. For full terms see the file doc/LICENSE.txt
 */

package com.zd.vpn;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.zd.vpn.alarm.AlarmReceiver;
import com.zd.vpn.core.ProfileManager;
import com.zd.vpn.service.CheckUtils;
import com.zd.vpn.service.StrategyService;


public class OnBootReceiver extends BroadcastReceiver {
//    AlarmReceiver alarm = new AlarmReceiver();
    // Debug: am broadcast -a android.intent.action.BOOT_COMPLETED
    @Override
    public void onReceive(Context context, Intent intent) {

        final String action = intent.getAction();

        if (Intent.ACTION_BOOT_COMPLETED.equals(action) || Intent.ACTION_MY_PACKAGE_REPLACED.equals(action)) {

//            alarm.setAlarm(context);

            boolean b = CheckUtils.isServiceWorked(context, "com.zd.vpn.service.StrategyService");
            if (!b) {
                Intent service = new Intent(context, StrategyService.class);
                context.startService(service);
            }

            VpnProfile bootProfile = ProfileManager.getLastConnectedProfile(context, true);
            if (bootProfile != null) {
                launchVPN(bootProfile, context);
            }
        }
    }

    void launchVPN(VpnProfile profile, Context context) {
        Intent startVpnIntent = new Intent(Intent.ACTION_MAIN);
        startVpnIntent.setClass(context, LaunchVPN.class);
        startVpnIntent.putExtra(LaunchVPN.EXTRA_KEY, profile.getUUIDString());
        startVpnIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startVpnIntent.putExtra(LaunchVPN.EXTRA_HIDELOG, true);

        context.startActivity(startVpnIntent);
    }


}
