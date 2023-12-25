package com.example.serena

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment

class DialogPairDevice : DialogFragment() {
    private lateinit var btnConfirm: Button
    private lateinit var credentials: EditText
    private lateinit var api: Service
    private var dismissListener: (() -> Unit)? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_device_credentials_dialog, null)
        btnConfirm = view.findViewById(R.id.confirm)
        credentials = view.findViewById(R.id.credentials)
        api = Service(requireActivity())
        builder.setView(view)
        btnConfirm.setOnClickListener {
            api.addSerenBox("My Seren Box",  credentials.text.toString(), object: Service.ApiCallback<IResponCreatedSerenBox> {
                override fun onSuccess(res: IResponCreatedSerenBox) {
                    requireActivity().runOnUiThread {
                        Toast.makeText(context, "Success Add SerenBox", Toast.LENGTH_SHORT).show()
                    }
                    dismiss()
                }
                override fun onFailure(error: IResponseApiError) {
                    requireActivity().runOnUiThread {
                        Toast.makeText(context, "Failed Add SerenBox: $error.message", Toast.LENGTH_SHORT).show()
                    }
                    dismiss()
                }
            })
        }
        return builder.create()
    }

    override fun onResume() {
        super.onResume()
    }

    fun setOnDismissListener(listener: () -> Unit) {
        dismissListener = listener
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        dismissListener?.invoke() // Memanggil listener saat dialog ditutup
    }

}

