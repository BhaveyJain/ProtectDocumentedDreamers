package com.bhavey.protectlegaldreamers

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatDialogFragment

public class InfoDialog : AppCompatDialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var builder = AlertDialog.Builder(activity)
        builder.setTitle("Our Story").setMessage("We are documented dreamers who came to the United States at a young age" +
                " along with our parents on H4, L2, or E2 visas." +
                " However, at age 21 we are expected to self-deport the U.S despite living and being educated here for most of our lives and not knowing any other country as home." +
                "We are not protected by programs like DACA which do not extend protection to documented dreamers." +
                "This app features the testimonies of us forgotten dreamers in hopes to raise awareness. If you are inspired to help, click the menu icon to take action.")
                .setPositiveButton("close", null)
        return builder.create()
    }
}