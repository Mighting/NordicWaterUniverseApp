//TODO Codes of Danger:
// Red = Fix ASAP
// Yellow = Fix when you can
// Green = Minor improvements

package zbc.linux.chatapp;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;


public class ClientActivity extends Activity {

    private ListView mList;
    private ArrayList<String> arrayList;
    private ClientListAdapter mAdapter;
    private TcpClient mTcpClient;

    //TODO (RED) Fix this shit, this is not okay
    // I want to make it so that network calls go on a different thread instead.
    public void enableStrictMode(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        enableStrictMode();

        arrayList = new ArrayList<String>();


        final Button retrieveArea1 = (Button) findViewById(R.id.RetrieveArea1_button);
        final Button retrieveArea2 = (Button) findViewById(R.id.RetrieveArea2_button);
        final Button retrieveArea3 = (Button) findViewById(R.id.RetrieveArea3_button);
        final Button retrieveArea4 = (Button) findViewById(R.id.RetrieveArea4_button);


        mList = (ListView) findViewById(R.id.list);
        mAdapter = new ClientListAdapter(this, arrayList);
        mList.setAdapter(mAdapter);


        retrieveArea1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                String message = retrieveArea1.getText().toString();
                arrayList.add("client: " + message);

                if (mTcpClient != null){
                    mTcpClient.sendMessage("Retrieve Area 1");
                    mTcpClient.sendMessage("1");
                }

                mAdapter.notifyDataSetChanged();
            }
        });

        retrieveArea2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                String message = retrieveArea2.getText().toString();
                arrayList.add("client: " + message);

                if (mTcpClient != null){
                    mTcpClient.sendMessage("Retrieve Area 2");
                    mTcpClient.sendMessage("2");
                }

                mAdapter.notifyDataSetChanged();
            }
        });

        retrieveArea3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                String message = retrieveArea3.getText().toString();
                arrayList.add("client: " + message);

                if (mTcpClient != null){
                    mTcpClient.sendMessage("Retrieve Area 3");
                    mTcpClient.sendMessage("3");
                }

                mAdapter.notifyDataSetChanged();
            }
        });

        retrieveArea4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                String message = retrieveArea4.getText().toString();
                arrayList.add("client: " + message);

                if (mTcpClient != null){
                    mTcpClient.sendMessage("Retrieve Area 4");
                    mTcpClient.sendMessage("4");
                }

                mAdapter.notifyDataSetChanged();
            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();

        mTcpClient.stopClient();
        mTcpClient = null;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (mTcpClient != null) {
            menu.getItem(1).setEnabled(true);
            menu.getItem(0).setEnabled(false);
        } else {
            menu.getItem(1).setEnabled(false);
            menu.getItem(0).setEnabled(true);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.connect:
                new ConnectTask().execute("");
                return true;
            case R.id.disconnect:
                mTcpClient.stopClient();
                mTcpClient = null;
                arrayList.clear();
                mAdapter.notifyDataSetChanged();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public class ConnectTask extends AsyncTask<String, String, TcpClient> {
        @Override
        protected TcpClient doInBackground(String... message) {
            mTcpClient = new TcpClient(new TcpClient.OnMessageReceived() {
                @Override
                public void messageReceived(String message) {
                    publishProgress(message);
                }
            });
            mTcpClient.run();
            return null;

        }

        @Override
        protected void onProgressUpdate(String... values){
            super.onProgressUpdate(values);
            arrayList.add(values[0]);
            mAdapter.notifyDataSetChanged();
        }

    }

}
