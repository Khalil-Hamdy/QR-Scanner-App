package com.khalil.qrscanner.ui.newscan.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.khalil.qrscanner.R;
import com.khalil.qrscanner.data.repositories.ScannedItemRepositoryImpl;
import com.khalil.qrscanner.databinding.FragmentNewScanBinding;
import com.khalil.qrscanner.domain.module.ScannedItem;
import com.khalil.qrscanner.ui.home.viewmodel.QRViewModel;
import com.khalil.qrscanner.ui.home.viewmodel.QRViewModelFactory;
import com.khalil.qrscanner.ui.newscan.viewmodel.ScanViewModel;
import com.khalil.qrscanner.ui.newscan.viewmodel.ScanViewModelFactory;

import java.util.ArrayList;
import java.util.List;


public class NewScanFragment extends Fragment {
    private FragmentNewScanBinding binding;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 100;
    private ArrayList<String> CodeList = new ArrayList<>();
    private DecoratedBarcodeView barcodeView;
    private boolean cameraPermissionGranted = false;
    private ScanViewModel scanViewModel;


    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState)
    {
        binding = FragmentNewScanBinding.inflate(inflater, container , false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        scanViewModel = new ViewModelProvider(this,
                new ScanViewModelFactory(
                        new ScannedItemRepositoryImpl(getActivity().getApplication())
                )).get(ScanViewModel.class);
        scanViewModel.insertItemsLiveData().observe(getViewLifecycleOwner(), items -> {
            Toast.makeText(requireContext(), "item add $items", Toast.LENGTH_LONG).show();
        });

        binding.textNewScan.setOnClickListener(v -> {
            binding.CodeAddedSuccessfully.setVisibility(View.GONE);
            binding.imgDone.setVisibility(View.GONE);
            barcodeView.resume();
        });

       /* binding.textNext.setOnClickListener(v ->
                NavHostFragment.findNavController(this)
                        .navigate(R.id.action_scanFragment_to_listScanShipmentsFragment)
        );*/

        barcodeView = binding.barcodeScannerView;
        barcodeView.decodeContinuous(barcodeCallback);

        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(NewScanFragment.this).popBackStack();
            }
        });
    }


    private final BarcodeCallback barcodeCallback = new BarcodeCallback() {
        @Override
        public void barcodeResult(BarcodeResult result) {
            if (result != null) {
                String text = result.getText();
                if (CodeList.contains(text)) {
                    Toast.makeText(requireContext(), "This Barcode scanned", Toast.LENGTH_LONG).show();
                    barcodeView.pause();
                } else {
                    CodeList.add(text);
                    binding.CodeAddedSuccessfully.setVisibility(View.VISIBLE);
                    binding.imgDone.setVisibility(View.VISIBLE);
                    barcodeView.pause();
                    scanViewModel.insert(
                            new ScannedItem(
                                    text,
                                    System.currentTimeMillis(),
                                    false)
                    );
                }
            }
        }

        @Override
        public void possibleResultPoints(List<ResultPoint> resultPoints) {
            // Not used
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        if (cameraPermissionGranted) {
            barcodeView.resume();
        } else {
            requestCameraPermission();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        barcodeView.pause();
    }

    private void requestCameraPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
        } else {
            cameraPermissionGranted = true;
            barcodeView.resume();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            cameraPermissionGranted = grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED;
            if (cameraPermissionGranted) {
                barcodeView.resume();
            }
        }
    }


}