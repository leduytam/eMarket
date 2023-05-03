package com.group05.emarket.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.group05.emarket.models.Address;
import com.group05.emarket.repositories.AddressRepository;

public class AddressViewModel extends ViewModel {
    AddressRepository addressRepository = AddressRepository.getInstance();

    private final MutableLiveData<Address> userAddress;

    public AddressViewModel() {
        userAddress = new MutableLiveData<>();
        fetch();
    }

    public void fetch() {
        addressRepository.getUserAddress().thenAccept(address -> {
            userAddress.setValue(address);
        }).exceptionally(e -> {
            e.printStackTrace();
            return null;
        });
    }

    public MutableLiveData<Address> getUserAddress() {
        return userAddress;
    }

}
