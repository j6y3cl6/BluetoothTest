package com.example.how.bluetoothtest;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Iterator;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private Button bt1,bt2;
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothDevice mBluetoothDevices;
    private BluetoothReceiver bluetoothReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED);

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                1);

        ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_CONTACTS);




        if(!getPackageManager().hasSystemFeature(getPackageManager().FEATURE_BLUETOOTH_LE)){
            Toast.makeText(getBaseContext(),".No_sup_ble",Toast.LENGTH_SHORT).show();
            finish();
        }else{
            Toast.makeText(getBaseContext(),".supble",Toast.LENGTH_SHORT).show();
        }
        findRid();

        IntentFilter intentFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        bluetoothReceiver = new BluetoothReceiver();
        registerReceiver(bluetoothReceiver,intentFilter);
        ConnectButton();
    }

    public void findRid(){
        bt1 = (Button) findViewById(R.id.bt1);
        bt2 = (Button) findViewById(R.id.bt2);
    }
    public void ConnectButton(){
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Scann();
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Scanner();
            }
        });

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        bluetoothAdapter.enable();
    }
    public void Scann(){
        Set<BluetoothDevice> device = bluetoothAdapter.getBondedDevices();
        if (device.size()>0){
            for (Iterator<BluetoothDevice> it = device.iterator();it.hasNext();){
                BluetoothDevice dev = (BluetoothDevice)it.next();
                Log.d("T1Address",dev.getAddress());
                Log.d("T1Name",dev.getName());
            }
        }

    }

    public void Scanner(){
        Log.d("搜尋開始","0");
//        bluetoothAdapter.startLeScan(mLeScanCallback);
        bluetoothAdapter.startDiscovery();
    }
    private BluetoothAdapter.LeScanCallback mLeScanCallback=new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(final BluetoothDevice device, final int rssi, byte[] scanRecord) {
            Log.d("How",device.getName());
            Log.d("How",device.getAddress());

        }

    };

}
