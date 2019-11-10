package com.zj.lib.noviceguide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zj on 2019-07-22.
 */
public class NoviceGuideSet {

    private List<NoviceGuide> noviceGuides = new ArrayList<>();

    private int position = 0;

    public void addGuide(NoviceGuide noviceGuide) {
        noviceGuides.add(noviceGuide);
        noviceGuide.setDismissCallBack(new DismissCallBack() {
            @Override
            public void onDismiss() {
                int nextPosition = position + 1;
                if (noviceGuides.size() > nextPosition) {
                    //不是最后一个
                    NoviceGuide next = noviceGuides.get(nextPosition);
                    position++;
                    next.show();
                }
            }
        });
    }

    public void show(){
        noviceGuides.get(0).show();
    }
}
