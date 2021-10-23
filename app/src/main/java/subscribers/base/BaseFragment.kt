package subscribers.base

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import subscribers.extensions.hideKeyboard

open class BaseFragment : Fragment() {

    protected fun hideKeyboard() {
        val parentActivity = requireActivity()
        if (parentActivity is AppCompatActivity) {
            parentActivity.hideKeyboard()
        }
    }
}