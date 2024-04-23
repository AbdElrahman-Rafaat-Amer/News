package com.abdelrahman.rafaat.news.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.abdelrahman.rafaat.news.utils.ConnectionLiveData

open class BaseFragment : Fragment() {
    protected var isInternetConnected = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkConnection()
    }

    private fun checkConnection() {
        ConnectionLiveData.getInstance(requireContext()).observe(viewLifecycleOwner) {
            if (isInternetConnected != it) {
                isInternetConnected = it
                if (it) {
                    Log.i("NetworkIssue", "BaseFragment checkConnection: ")
                    onConnected()

                } else {
                    Log.i("NetworkIssue", "BaseFragment checkConnection: ")
                    onDisconnected()
                }
            }
        }
    }

    protected open fun onConnected() {
        Log.i("NetworkIssue", "BaseFragment onConnected: ")
    }

    protected open fun onDisconnected() {
        Log.i("NetworkIssue", "BaseFragment onDisconnected: ")
    }

}