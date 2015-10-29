package com.example.nitu.popularmovies;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {
    private static final String LOG_TAG = DetailActivityFragment.class.getSimpleName();
    private TrailerAdapter mTrailerAdapter;

    public DetailActivityFragment() {
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mTrailerAdapter.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.fragment_detail, container, false);
        Intent intent = getActivity().getIntent();
        View rootView =  inflater.inflate(R.layout.fragment_detail, container, false);
        if (intent != null && intent.hasExtra(intent.EXTRA_TEXT))
        {
            MovieData movie = (MovieData)intent.getParcelableExtra(intent.EXTRA_TEXT);
            String mMovieId= movie.getMovie_id();
            if (savedInstanceState != null) {
                mTrailerAdapter = new TrailerAdapter(getActivity());
                mTrailerAdapter.onRestoreInstanceState(savedInstanceState);
            }
            else {
                updateTrailer(mMovieId);
                mTrailerAdapter = new TrailerAdapter(getActivity());
            }
            String mMovieTitle = movie.getTitle();
            ((TextView)rootView.findViewById(R.id.title_text)).setText(mMovieTitle);
            //getActivity().setTitle(mMovieTitle);
            String url =movie.getPoster_path();
            if (Patterns.WEB_URL.matcher(url).matches())
                Picasso.with(getActivity()).load(url).into((ImageView) rootView.findViewById(R.id.imageView));
           /* String mMovieVoteCount = " ("+ movie.getVote_count()+" Votes)";
            ((TextView)rootView.findViewById(R.id.votecount_text))
                    .setText(mMovieVoteCount);*/
            String mMovieVoteAverage = movie.getVote_average();
            ((TextView)rootView.findViewById(R.id.voteaverage_text))
                    .setText(mMovieVoteAverage +"/10");
            Float f= Float.parseFloat(mMovieVoteAverage);
            ((RatingBar)rootView.findViewById(R.id.ratingBar)).setRating(f);
            String mMovieReleaseDate = movie.getRelease_date();
            ((TextView)rootView.findViewById(R.id.release_text))
                    .setText(mMovieReleaseDate);
            String mMovieOverview = movie.getOverview();
            ((TextView)rootView.findViewById(R.id.overview_text))
                    .setText(mMovieOverview);
            ListView listView = (ListView) rootView.findViewById(R.id.trailerview);
            listView.setAdapter(mTrailerAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    TrailerData trailer = mTrailerAdapter.getItem(position);
                    /*Intent intent = new Intent(getActivity(), DetailActivity.class)
                            .putExtra(Intent.EXTRA_TEXT, movie);
                    startActivity(intent);*/
                }
            });
        }
        return rootView;
    }

    private void updateTrailer(String movieId) {
        FetchTrailerTask trailerTask = new FetchTrailerTask();
        Toast.makeText(getActivity(), "Loading Please Wait..", Toast.LENGTH_LONG).show();
        if (NetworkUtils.getInstance(getContext()).isOnline())
            trailerTask.execute(movieId);
        else {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Network is not available");
            builder.setPositiveButton(R.string.action_exit, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    //getActivity().finish();
                }
            });
            builder.create();
            builder.show();
        }
    }

    public class FetchTrailerTask extends AsyncTask<String,Void,List<TrailerData>>
    {
        private final String LOG_TAG = FetchTrailerTask.class.getSimpleName();
        ProgressDialog progressDialog;
        @Override
        protected List<TrailerData> doInBackground(String... params) {
            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            // Will contain the raw JSON response as a string.
            // Will contain the raw JSON response as a string.
            String trailerJsonStr = null;

            // Please Enter the key below
            //String apiKeyStr ="[YOUR API KEY]";
            String apiKeyStr = "7537b743615a000671a98c32d354df39";
            try {
                final String FORECAST_BASE_URL = "http://api.themoviedb.org/3/movie/"+ params[0]+"/videos?";
                final String APIKEY_PARAM = "api_key";

                Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                        .appendQueryParameter(APIKEY_PARAM, apiKeyStr)
                        .build();

                URL url = new URL(builtUri.toString());

                Log.v(LOG_TAG, "Getting Data from :   " + url);

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {

                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                trailerJsonStr = buffer.toString();

            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }
            try {
                return getTrailerDataFromJson(trailerJsonStr);
            } catch (JSONException j) {
                Log.e(LOG_TAG, "JSON Error", j);
            }
            return null;
        }

        private List<TrailerData> getTrailerDataFromJson(String trailerJsonStr)
                throws JSONException {
            try {
                JSONObject trailerJson = new JSONObject(trailerJsonStr);
                JSONArray trailerArray = trailerJson.getJSONArray("results");
                List<TrailerData> trailers = new ArrayList<TrailerData>();
                for (int i = 0; i < trailerArray.length(); i++) {
                    JSONObject trailer = trailerArray.getJSONObject(i);
                    String trailerId = trailer.getString("id");
                    String trailerKey = trailer.getString("key");
                    String trailerName = trailer.getString("name");
                    String trailerSite = trailer.getString("site");
                    String trailerSize = trailer.getString("size");

                    TrailerData trailerData = new TrailerData();
                    trailerData.setTrailerId(trailerId);
                    trailerData.setTrailerKey(trailerKey);
                    trailerData.setTrailerName(trailerName);
                    trailerData.setTrailerSite(trailerSite);
                    trailerData.setTrailerSize(trailerSize);
                    trailers.add(trailerData);
                }
                return trailers;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //Json Parsing code end

            return null;
        }

        @Override
        protected void onPreExecute(){
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("Downloading...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
        @Override
        protected void onPostExecute(List<TrailerData> result) {
            if (result != null){
                mTrailerAdapter.replace(result);
            }
            progressDialog.cancel();
        }
        @Override
        protected void onProgressUpdate(Void... values) {
            // Do nothing
        }

    }
}
