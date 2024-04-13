package he.chen.coroutinedemo

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import he.chen.coroutinedemo.api.Api1
import he.chen.coroutinedemo.api.Api2
import he.chen.coroutinedemo.databinding.FragmentFirstBinding
import he.chen.coroutinedemo.repo.TestRepo1
import he.chen.coroutinedemo.repo.TestRepo2
import he.chen.coroutinedemo.utils.TAG
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
//        lifecycleScope.launch {
//            doSuspend()
//        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}