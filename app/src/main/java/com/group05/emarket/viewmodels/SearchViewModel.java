package com.group05.emarket.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.group05.emarket.models.Category;
import com.group05.emarket.repositories.CategoryRepository;

import java.util.ArrayList;
import java.util.List;

public class SearchViewModel extends ViewModel {
    public CategoryRepository categoryRepo = CategoryRepository.getInstance();

    private final MutableLiveData<List<Category>> categories;
    private final MutableLiveData<Boolean> isLoading;

    public SearchViewModel() {
        categories = new MutableLiveData<>(new ArrayList<>());
        isLoading = new MutableLiveData<>(false);

        fetch();
    }

    public MutableLiveData<List<Category>> getCategories() {
        return categories;
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public void fetch() {
        if (isLoading.getValue() == null || isLoading.getValue()) {
            return;
        }

        isLoading.postValue(true);

        var categoriesFuture = categoryRepo.getCategories();

        categoriesFuture.thenAcceptAsync(categories -> {
            this.categories.postValue(categories);
            isLoading.postValue(false);
        }).exceptionally(e -> {
            isLoading.postValue(false);
            return null;
        });
    }
}
