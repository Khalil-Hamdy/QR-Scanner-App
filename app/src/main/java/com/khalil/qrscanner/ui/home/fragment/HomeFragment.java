package com.khalil.qrscanner.ui.home.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.khalil.qrscanner.R;
import com.khalil.qrscanner.data.repositories.ScannedItemRepositoryImpl;
import com.khalil.qrscanner.databinding.FragmentHomeBinding;
import com.khalil.qrscanner.ui.home.adapter.HistoryAdapter;
import com.khalil.qrscanner.ui.home.viewmodel.QRViewModel;
import com.khalil.qrscanner.ui.home.viewmodel.QRViewModelFactory;


public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;

    private QRViewModel qrViewModel;
    private RecyclerView recyclerView;
    private HistoryAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        qrViewModel = new ViewModelProvider(this,
                new QRViewModelFactory(
                        new ScannedItemRepositoryImpl(getActivity().getApplication())
                )).get(QRViewModel.class);

        binding.imageScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(HomeFragment.this)
                        .navigate(R.id.action_homeFragment_to_newScanFragment);
            }
        });
        recyclerView = view.findViewById(R.id.historyRecyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new HistoryAdapter(requireContext(), (item, position) -> {
            item.setFavorite(!item.isFavorite());
            adapter.notifyDataSetChanged();
            qrViewModel.toggleFavorite(item);
        });
        recyclerView.setAdapter(adapter);

        qrViewModel.fetchItems();

        qrViewModel.getItemsLiveData().observe(getViewLifecycleOwner(), items -> {
            adapter.setItems(items);
        });
        qrViewModel.getFavoritesLiveData().observe(getViewLifecycleOwner(), items -> {
            adapter.setItems(items);
        });


        binding.allItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qrViewModel.fetchItems();
                binding.favItems.setBackgroundColor(
                        ContextCompat.getColor(
                                requireContext(),
                                R.color.white
                        )
                );
                binding.allItems.setBackgroundColor(
                        ContextCompat.getColor(
                                requireContext(),
                                R.color.primary_color
                        )
                );
            }
        });
        binding.favItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qrViewModel.fetchFavorites();
                binding.favItems.setBackgroundColor(
                        ContextCompat.getColor(
                                requireContext(),
                                R.color.primary_color
                        )
                );
                binding.allItems.setBackgroundColor(
                        ContextCompat.getColor(
                                requireContext(),
                                R.color.white
                        )
                );
            }
        });


    }

}