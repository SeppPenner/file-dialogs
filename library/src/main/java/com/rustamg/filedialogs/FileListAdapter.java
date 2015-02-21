package com.rustamg.filedialogs;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import java.io.File;
import java.text.DateFormat;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * Created at 30/01/15 21:18
 *
 * @author rustamg
 */
public class FileListAdapter extends RecyclerView.Adapter<FileListAdapter.FileViewHolder> {

    private static DateFormat sModificationTimeFormat = DateFormat.getDateTimeInstance();

    private final File[] mFiles;
    private final LayoutInflater mInflater;
    private final OnFileSelectedListener mOnFileSelectedListener;

    public FileListAdapter(Context context, File[] files, OnFileSelectedListener fileSelectedListener) {

        mFiles = files;
        mInflater = LayoutInflater.from(context);
        mOnFileSelectedListener = fileSelectedListener;
    }

    @Override
    public FileViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = mInflater.inflate(R.layout.list_item_file, parent, false);

        return new FileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final FileViewHolder holder, int position) {

        holder.bind(mFiles[position]);

        holder.getView().setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (mOnFileSelectedListener != null) {
                    mOnFileSelectedListener.onFileSelected(holder.getFile());
                }
            }
        });
    }

    @Override
    public int getItemCount() {

        return mFiles.length;
    }

    public class FileViewHolder extends RecyclerView.ViewHolder {

        private View mView;
        private File mFile;

        @InjectView(R.id.tv_file_name)
        protected TextView mFileNameText;
        @InjectView(R.id.tv_file_modified)
        protected TextView mFileModifiedText;
        @InjectView(R.id.iv_file_icon)
        protected ImageView mFileIcon;

        public FileViewHolder(View itemView) {

            super(itemView);

            mView = itemView;
            ButterKnife.inject(this, itemView);
        }

        public void bind(File file) {

            mFile = file;

            if (file.isDirectory()) {

                mFileIcon.setImageResource(R.drawable.ic_folder);
            }
            else {
                mFileIcon.setImageResource(R.drawable.ic_file);
            }

            mFileNameText.setText(file.getName());
            mFileModifiedText.setText(sModificationTimeFormat.format(new Date(file.lastModified())));
        }

        public File getFile() {

            return mFile;
        }

        public View getView() {

            return mView;
        }
    }


    public interface OnFileSelectedListener {

        void onFileSelected(File item);
    }
}
