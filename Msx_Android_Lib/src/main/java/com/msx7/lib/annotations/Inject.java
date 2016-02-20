package com.msx7.lib.annotations;

import java.lang.reflect.Field;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.View;

public class Inject {

    /**
     *
     * @param activity
     */
    public static final void inject(Activity activity) {
        if (activity == null)
            return;
        InjectActivity mActivityLayout = activity.getClass().getAnnotation(InjectActivity.class);
        if (mActivityLayout != null) {
            activity.setContentView(mActivityLayout.value());
        }
        Field[] mFields = activity.getClass().getDeclaredFields();
        for (Field field : mFields) {
            InjectView mViewId = field.getAnnotation(InjectView.class);
            if (mViewId == null)
                continue;
            if (mViewId.value() == 0)
                continue;
            field.setAccessible(true);
            View _tmp = null;
            if (mViewId.parent() != -1) {
                _tmp = activity.findViewById(mViewId.parent()).findViewById(mViewId.value());
            }else{
                activity.findViewById(mViewId.value());
            }
            try {
                field.set(activity, _tmp);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *
     * @param fragment
     * @return
     */
    public  static final  View inject(Fragment fragment) {
        if (fragment == null)
            return null;
        InjectActivity mActivityLayout = fragment.getClass().getAnnotation(InjectActivity.class);
        View view = null;
        if (mActivityLayout != null) {
            view = fragment.getLayoutInflater(null).inflate(mActivityLayout.value(), null);
        } else {
            view = fragment.getView();
        }
        inject(fragment, view);
        return view;
    }

    /**
     *
     * @param obj
     * @param rootView
     */
    public static final void inject(Object obj,View rootView){
        if(rootView==null||obj==null)return ;
        Field[] mFields = obj.getClass().getDeclaredFields();
        for (Field field : mFields) {
            InjectView mViewId = field.getAnnotation(InjectView.class);
            if (mViewId == null)
                continue;
            if (mViewId.value() == -1)
                continue;
            View _tmp = rootView;
            if (mViewId.parent() != -1) {
                _tmp = rootView.findViewById(mViewId.parent());
            }
            field.setAccessible(true);
            try {
                field.set(obj, _tmp.findViewById(mViewId.value()));
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
