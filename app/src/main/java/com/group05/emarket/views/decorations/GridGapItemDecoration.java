package com.group05.emarket.views.decorations;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GridGapItemDecoration extends RecyclerView.ItemDecoration {
    private final int _spanCount;
    private final int _spacing;
    private final boolean _isIncludeEdge;

    public GridGapItemDecoration(int spanCount, int spacing, boolean includeEdge) {
        this._spanCount = spanCount;
        this._spacing = spacing;
        this._isIncludeEdge = includeEdge;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        int column = position % _spanCount;

        if (_isIncludeEdge) {
            outRect.left = _spacing - column * _spacing / _spanCount;
            outRect.right = (column + 1) * _spacing / _spanCount;

            if (position < _spanCount) {
                outRect.top = _spacing;
            }
            outRect.bottom = _spacing;
        } else {
            outRect.left = column * _spacing / _spanCount;
            outRect.right = _spacing - (column + 1) * _spacing / _spanCount;
            if (position >= _spanCount) {
                outRect.top = _spacing;
            }
        }
    }
}
