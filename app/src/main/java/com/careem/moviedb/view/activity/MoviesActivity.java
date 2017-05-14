package com.careem.moviedb.view.activity;

import android.app.DatePickerDialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.DatePicker;

import com.careem.moviedb.R;
import com.careem.moviedb.databinding.ActivityMoviesBinding;
import com.careem.moviedb.di.DaggerMoviesComponent;
import com.careem.moviedb.view.adapter.MoviesAdapter;
import com.careem.moviedb.viewmodel.MoviesViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

public class MoviesActivity extends BaseActivity {

    private ActivityMoviesBinding binding;
    private DatePickerDialog datePickerDialog;
    private boolean isStartDate = false;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
    private Date startDate;
    private Date endDate;

    @Inject
    protected MoviesViewModel moviesViewModel;

    @Inject
    protected MoviesAdapter moviesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movies);
        moviesViewModel.onCreate();
        initViews();
    }

    private void initViews() {
        binding.moviesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.moviesRecyclerView.setAdapter(moviesAdapter);
        binding.moviesRecyclerView.addOnScrollListener(moviesViewModel.getScrollListener(
                (LinearLayoutManager) binding.moviesRecyclerView.getLayoutManager()));

        DatePickerDialog.OnDateSetListener dateSetListener = new
                DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker arg0,
                                          int year, int month, int day) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, month, day);
                        String date = simpleDateFormat.format(calendar.getTime());
                        if (isStartDate) {
                            startDate = calendar.getTime();
                            binding.startDateText.setText(date);
                        } else {
                            endDate = calendar.getTime();
                            binding.endDateText.setText(date);
                        }
                    }
                };
        Calendar calendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, dateSetListener, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        binding.startDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isStartDate = true;
                datePickerDialog.show();
            }
        });
        binding.endDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isStartDate = false;
                datePickerDialog.show();
            }
        });
        binding.filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (startDate == null || endDate == null) {
                    viewUtil.showToast(R.string.date_required_error);
                } else if (endDate.before(startDate)) {
                    viewUtil.showToast(R.string.date_error);
                } else {
                    moviesViewModel.loadMoviesByDate(binding.startDateText.getText().toString(),
                            binding.endDateText.getText().toString());
                }
            }
        });
    }

    @Override
    protected void finishInjection() {
        DaggerMoviesComponent.builder().applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule()).build().inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.startDateText.setText("");
        binding.endDateText.setText("");
        moviesViewModel.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        moviesViewModel.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        moviesViewModel.onDestroy();
    }
}
