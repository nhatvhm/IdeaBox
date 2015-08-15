package vn.alovoice.ideabox;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Vector;


/**
 * A placeholder fragment containing a simple view.
 */
public class listIdeaFragment extends Fragment {
    private static final String TAG = listIdeaFragment.class.getSimpleName();
    ArrayAdapter<String> mListIdeaAdapter;

    public listIdeaFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.
            setHasOptionsMenu(true);
        }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.ideafragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            fetchIdeaAsync ideaTask = new fetchIdeaAsync();
            ideaTask.execute();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        // Create some dummy data for the ListView.  Here are some sample ideas
        String[] data = {
            "Ngày 20/2/2013 \n - Thiết kế máy hút bụi tự động \n- 0903757444",
            "Ngày 22/2/2013 \n - Bộ ra dây nhảy đa năng \n - 0918103366",
            "Ngày 23/2/2013 \n - Bộ hiển thị số điện thoại loại lớn \n- 0909090909",
            "Ngày 25/3/2013 \n - Bộ xem phim trên Android \n - 0903757666",
            "Ngay 02/4/2013 \n - Máy điện thoại có độ phân giải cao \n - 0903709000",
            "Ngày 05/4/2013 \n - Máy bán hàng tự động \n - 090375723423",
            "Ngày 06/5/2013 \n - Bộ xem lý lịch nhân vật ở tượng đài \n - 0903563566"
        };
        List<String> listIdea = new ArrayList<String>(Arrays.asList(data));

        mListIdeaAdapter =
                 new ArrayAdapter<String>(
                     getActivity(), // The current context (this activity)
                     R.layout.list_item_idea, // The name of the layout ID.
                     R.id.list_item_idea_textview, // The ID of the textview to populate.
                     listIdea);

        ListView listView = (ListView) rootView.findViewById(R.id.listview_idea);
        listView.setAdapter(mListIdeaAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String ideaItem = mListIdeaAdapter.getItem(position);

                Toast.makeText(getActivity(), ideaItem, Toast.LENGTH_LONG).show();
            }
        });


        return rootView;
    }

    private class fetchIdeaAsync extends AsyncTask<Void, Void,  ArrayList<idea>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<idea> doInBackground(Void... params) {
            //idea mIdea=params[0];
            return doGetIdeas(getActivity());

        }

        @Override
        protected void onPostExecute(ArrayList<idea> ideaArray) {
            super.onPostExecute(ideaArray);
            List<String> listIdea = new ArrayList<String>();
            for (idea simpleIdea : ideaArray) {
                //Log.e(TAG, String.format("%s : %s : %s", simpleIdea.getDienThoai(), simpleIdea.getNoiDung(), simpleIdea.getNgayTao()));

                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                String day = formatter.format(simpleIdea.getNgayTao());

                String threeFieldIdea = String.format("Ngày %s \n: %s \n: %s", day, simpleIdea.getNoiDung(), simpleIdea.getDienThoai());
                listIdea.add(threeFieldIdea);
                //Log.e(TAG, String.format("%s: nok%s : ", simpleIdea.getDienThoai(), simpleIdea.getNoiDung()));
            }
            mListIdeaAdapter.clear();
            mListIdeaAdapter.addAll(listIdea);
            //Toast.makeText(getActivity(), "KQ: " + s, Toast.LENGTH_LONG).show();
        }
    }


    public static ArrayList<idea> doGetIdeas(Context ct) {
        ArrayList<idea> arrIdea = new ArrayList<idea>();
        try{
            final String URL="http://www.alovoice.vn/food.php";
            final String NAMESPACE="http://www.alovoice.vn/foodservice";
            final String METHOD_NAME="food.getIdeas";

            //final String SOAP_ACTION=NAMESPACE+METHOD_NAME;
            final String SOAP_ACTION="http://www.alovoice.vn/foodservice#getIdeas";
            SoapObject request=new SoapObject(NAMESPACE, METHOD_NAME);


            //int ID=Integer.parseInt(id_luotgiao);
            PropertyInfo i_ngaytao = new PropertyInfo();
            i_ngaytao.setName("ngaytao");
            i_ngaytao.setValue("");//id_nhanvientc
            i_ngaytao.setType(String.class);
            request.addProperty(i_ngaytao);

            SoapSerializationEnvelope envelope= new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet=true;
            envelope.setOutputSoapObject(request);
            //N?u truy?n s? th?c trên m?ng b?t bu?c ph?i ??ng ký MarshalFloat
            //không có nó thì b? báo l?i
            //MarshalFloat marshal=new MarshalFloat();
            // marshal.register(envelope);

            HttpTransportSE androidHttpTransport= new HttpTransportSE(URL);
            androidHttpTransport.debug=true;
            //SSLConnection.allowAllSSL();
            androidHttpTransport.call(SOAP_ACTION, envelope);

            //String kq = androidHttpTransport.requestDump;

            //Get Array Catalog into soapArray
            //SoapObject soapArray=(SoapObject) envelope.getResponse();
            Vector<?> soapArray = (Vector<?>) envelope.getResponse();
            //SoapObject soapArray=(SoapObject) envelope.bodyIn;
            //final int size = soapArray.size();
            //arrlg.clear();
            Vector<?> soapArray_data = (Vector<?>)soapArray.get(1);

            //Log.e(TAG, soapArray_data.size() + "");
            //Toast.makeText(getBaseContext(),  "KQ: " + size, Toast.LENGTH_LONG).show();

            for(int i=0;i<soapArray_data.size();i++){
                SoapObject soapObject=(SoapObject)soapArray_data.get(i);

                int mid =Integer.parseInt(soapObject.getPropertyAsString("id"));
                String mNoiDung = soapObject.getPropertyAsString("noidung");
                String mNgayTao = soapObject.getPropertyAsString("ngaytao");
                String mDienThoai = soapObject.getPropertyAsString("dienthoai");

                //??y vào array
                //Luotgiao lg = new Luotgiao("ID_luotgiao", luotgiao, "nhanvientc", soluong_hd, sotien, "ngaygiao","ghichu");
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                String dateInString = "07/06/2013";
                Date date = formatter.parse(dateInString);

                idea mIdea = new idea(mid, mNoiDung, date, mDienThoai);
                arrIdea.add(mIdea);

                //Log.e(TAG, mNoiDung);
            }
            return arrIdea;
        } catch(Exception e){
            //Toast.makeText(ct, "L?i nè! " + e.getMessage(), Toast.LENGTH_LONG).show();
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    private String getReadableDateString(long time){
        // Because the API returns a unix timestamp (measured in seconds),
        // it must be converted to milliseconds in order to be converted to valid date.
        SimpleDateFormat shortenedDateFormat = new SimpleDateFormat("EEE MMM dd");
             return shortenedDateFormat.format(time);
        }

}
