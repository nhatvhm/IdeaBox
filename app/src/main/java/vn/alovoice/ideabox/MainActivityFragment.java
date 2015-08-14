package vn.alovoice.ideabox;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ArrayAdapter<String> mForecastAdapter;

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

        mForecastAdapter =
                 new ArrayAdapter<String>(
                     getActivity(), // The current context (this activity)
                     R.layout.list_item_idea, // The name of the layout ID.
                     R.id.list_item_idea_textview, // The ID of the textview to populate.
                     listIdea);

        ListView listView = (ListView) rootView.findViewById(R.id.listview_idea);
        listView.setAdapter(mForecastAdapter);

        return rootView;
    }
}
