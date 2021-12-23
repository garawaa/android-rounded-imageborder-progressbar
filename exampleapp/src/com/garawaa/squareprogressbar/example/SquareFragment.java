package com.garawaa.squareprogressbar.example;

import com.garawaa.squareprogressbar.SquareProgressBar;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import java.util.Random;

public class SquareFragment extends Fragment {
	public SquareProgressBar squareProgressBar;
    private SeekBar progressSeekBar, widthSeekBar;

    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(com.garawaa.squareprogressbar.example.R.layout.square_layout, container, false);

        final TextView progressView = (TextView) view
                .findViewById(com.garawaa.squareprogressbar.example.R.id.progressDisplay);
        progressView.setText("32%");

        squareProgressBar = (SquareProgressBar) view.findViewById(com.garawaa.squareprogressbar.example.R.id.subi2);
		squareProgressBar.setRoundedCorners(true, 45);
		squareProgressBar.setImageCornerRadius(45);
        squareProgressBar.setImage(R.drawable.holyroodpark);
		squareProgressBar.setColor("#FF0000");
		squareProgressBar.setProgress(32);
		squareProgressBar.setWidth(8);

        squareProgressBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Random random = new Random();

                // random progress
                setProgressBarProgress(random.nextInt(100), progressView);

                // random width
                int randWidth = random.nextInt(17) + 4;
                widthSeekBar.setProgress(randWidth);
                squareProgressBar.setWidth(randWidth);

                // random colour
                squareProgressBar.setColorRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256));
            }
        });

        progressSeekBar = (SeekBar) view
				.findViewById(com.garawaa.squareprogressbar.example.R.id.progressSeekBar);
		progressSeekBar
				.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

					@Override
					public void onStopTrackingTouch(SeekBar seekBar) {
						// nothing to do
					}

					@Override
					public void onStartTrackingTouch(SeekBar seekBar) {
						// nothing to do
					}

					@Override
					public void onProgressChanged(SeekBar seekBar,
							int progress, boolean fromUser) {
                        setProgressBarProgress(progress, progressView);
					}
				});

		widthSeekBar = (SeekBar) view.findViewById(com.garawaa.squareprogressbar.example.R.id.widthSeekBar);
		widthSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// nothing to do
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// nothing to do
			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				squareProgressBar.setWidth(progress);
			}
		});
		return view;
	}

    private void setProgressBarProgress(int progress, TextView progressView) {
        squareProgressBar.setProgress(progress);
        progressView.setText(progress + "%");
        progressSeekBar.setProgress(progress);
    }

}
