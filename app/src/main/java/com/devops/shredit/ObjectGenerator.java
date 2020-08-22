package com.devops.shredit;

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.io.File;

public class ObjectGenerator {

    protected Permissions PermissionObj() {
        return new Permissions();
    }

    protected Security SecurityObj() {
        return new Security();
    }

    protected DesignedViews DesignViewObj() {
        return new DesignedViews();
    }

    protected About AboutObj() {
        return new About();
    }

    protected IntentCaller IntentCallerObj() {
        return new IntentCaller();
    }

    protected Object AlgoShredderObj() {
        return new AlgoShreddingScreen();
    }

    protected Object FileHelperObj() {
        return new FileHelper();
    }

    protected Object HelpObj() {
        return new Help();
    }

    protected Object TestBedObj() {
        return new Help();
    }

    protected Generalhelper GeneralHelperObj() {
        return new Generalhelper();
    }

    protected Object HomeScreenObj() {
        return new Homescreen();
    }

    protected Shredder ShredderObj() {
        return new Shredder();
    }

    protected Object SupportObj() {
        return new Support();
    }

}
