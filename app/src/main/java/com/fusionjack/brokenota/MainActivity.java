/*
 * Copyright (C) 2015 Chandra Poerwanto
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.fusionjack.brokenota;

import android.app.Fragment;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.MenuItem;

import com.fusionjack.brokenota.configs.LinkConfig;
import com.fusionjack.brokenota.dialogs.WaitDialogFragment;
import com.fusionjack.brokenota.fragments.BrokenOTAFragment;

public class MainActivity extends PreferenceActivity implements
        WaitDialogFragment.OTADialogListener, LinkConfig.LinkConfigListener {

    private static final String FRAGMENT_TAG = BrokenOTAFragment.class.getName();
    private BrokenOTAFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFragment = (BrokenOTAFragment) getFragmentManager().findFragmentByTag(FRAGMENT_TAG);
        if (mFragment == null) {
            getFragmentManager().beginTransaction()
                    .replace(android.R.id.content, new BrokenOTAFragment(), FRAGMENT_TAG)
                    .commit();
        }

        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onProgressCancelled() {
        Fragment fragment = getFragmentManager().findFragmentByTag(FRAGMENT_TAG);
        if (fragment instanceof WaitDialogFragment.OTADialogListener) {
            ((WaitDialogFragment.OTADialogListener) fragment).onProgressCancelled();
        }
    }

    @Override
    public void onConfigChange() {
        Fragment fragment = getFragmentManager().findFragmentByTag(FRAGMENT_TAG);
        if (fragment instanceof LinkConfig.LinkConfigListener) {
            ((LinkConfig.LinkConfigListener) fragment).onConfigChange();
        }
    }
}
