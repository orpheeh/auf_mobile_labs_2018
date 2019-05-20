package com.example.norph.scooladascphysique3eme;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ChaptersAdapter extends RecyclerView.Adapter<ChaptersAdapter.ChapterHolder> {

    private List<TableOfContentActivity.Chapter> mList;

    private OnItemClickListener mListener;

    private int mColor;

    public ChaptersAdapter(List<TableOfContentActivity.Chapter> list, int color) {
        mList = list;

        mColor = color;
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public static interface OnItemClickListener {
        void onClick(View v, int position);
    }

    @NonNull
    @Override
    public ChapterHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from( viewGroup.getContext() ).inflate( R.layout.layout_chapter, viewGroup, false );

        ChapterHolder chapterHolder = new ChapterHolder( view );

        return chapterHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ChapterHolder chapterHolder, int i) {

        chapterHolder.textView.setText( mList.get( i ).title );

        chapterHolder.textView.setTextColor(mColor);

        chapterHolder.imageView.setImageResource( mList.get( i ).illustration );

        final int position = i;

        chapterHolder.view.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClick( v, position );
            }
        } );

        chapterHolder.textView2.setText( Html.fromHtml(mList.get( i ).comment) );
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ChapterHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public ImageView imageView;

        public  TextView textView2;

        public View view;

        public ChapterHolder(@NonNull View itemView) {
            super( itemView );

            textView = itemView.findViewById( R.id.title );

            imageView = itemView.findViewById( R.id.illustration );

            textView2 = itemView.findViewById( R.id.comment );

            view = itemView;
        }
    }

}
