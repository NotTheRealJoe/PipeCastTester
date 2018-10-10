package co.joet.pipecasttester;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import org.schabi.newpipe.cast.Device;
import org.schabi.newpipe.cast.protocols.chromecast.ChromecastDiscoverer;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MyService extends Service {
    private ChromecastDiscoverer chromecastDiscoverer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            this.chromecastDiscoverer = new ChromecastDiscoverer();
            Toast.makeText(this, "Discovery Started", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(this, "Starting discovery failed!", Toast.LENGTH_LONG).show();
        }
        return START_STICKY;
    }

    public List<Device> getChromecasts() throws InterruptedException, ExecutionException, IOException {
        return chromecastDiscoverer.discoverDevices();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            chromecastDiscoverer.finalize();
        } catch (IOException e) {
            Toast.makeText(this, "Failed to stop discovery", Toast.LENGTH_LONG).show();
        }
        Toast.makeText(this, "Service destroyed", Toast.LENGTH_LONG).show();
    }
}
