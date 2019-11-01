package com.example.calculatetest;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateVMFactory;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.calculatetest.databinding.FragmentHomeBinding;
import com.example.calculatetest.databinding.FragmentTestBinding;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_home, container, false);
        MyViewModel myViewModel;
        myViewModel = ViewModelProviders.of(requireActivity(), new SavedStateVMFactory(requireActivity())).get(MyViewModel.class);
        FragmentHomeBinding binding;
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        binding.setData(myViewModel);
        binding.setLifecycleOwner(requireActivity());
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController controller = Navigation.findNavController(v);
                controller.navigate(R.id.action_homeFragment_to_testFragment);
            }
        });
        return binding.getRoot();
    }

}
