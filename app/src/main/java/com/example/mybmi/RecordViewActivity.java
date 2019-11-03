package com.example.mybmi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.util.ArrayList;
import java.util.List;

public class RecordViewActivity extends AppCompatActivity {

    private LineChart chart;
    private List<RecordBean> list = new ArrayList<>();
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_view);

        chart = findViewById(R.id.chart_view);
        listView = findViewById(R.id.list_view);

        //初始化本地数据
        initLocal();

        ListAdapter bmiAdapter = new ListAdapter(RecordViewActivity.this, R.layout.bmi_item, list);
        listView.setAdapter(bmiAdapter);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        chart.setData(getLineData());

    }

    private void initLocal() {
        list = LitePal.findAll(RecordBean.class);
    }

    private LineData getLineData() {
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            entries.add(new Entry(i, (float) list.get(i).getResult()));
        }
        //一个LineDataSet就是一条线
        LineDataSet lineDataSet = new LineDataSet(entries, "BMI");
        //平滑曲线
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        LineData data = new LineData(lineDataSet);
        return data;
    }
}
