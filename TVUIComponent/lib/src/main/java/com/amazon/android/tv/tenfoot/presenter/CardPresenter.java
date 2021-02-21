/**
 * Copyright 2015-2016 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *     http://aws.amazon.com/apache2.0/
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
/*
 * Copyright (C) 2015 The Android Open Source Project
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
package com.amazon.android.tv.tenfoot.presenter;

import com.amazon.android.contentbrowser.ContentBrowser;
import com.amazon.android.model.Action;
import com.amazon.android.model.content.Content;
import com.amazon.android.model.content.ContentContainer;
import com.amazon.android.tv.tenfoot.base.TenFootApp;
import com.amazon.android.tv.tenfoot.utils.ContentHelper;
import com.amazon.android.utils.GlideHelper;
import com.amazon.android.utils.Helpers;
import com.amazon.android.tv.tenfoot.R;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.zype.fire.api.AppConfiguration;
import com.zype.fire.api.ZypeConfiguration;
import com.zype.fire.api.ZypeSettings;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import androidx.cardview.widget.CardView;
import androidx.leanback.widget.BaseCardView;
import androidx.leanback.widget.ImageCardView;
import androidx.leanback.widget.Presenter;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import static com.zype.fire.api.ZypeSettings.SHOW_TITLE;

/**
 * A CardPresenter is used to generate Views and bind Objects to them on demand.
 * It contains an Image CardView
 */
public class CardPresenter extends Presenter {

    private static final String TAG = CardPresenter.class.getSimpleName();

    private int mCardWidthDp;
    private int mCardHeightDp;

    private Drawable mDefaultCardImage;
    private static Drawable sFocusedFadeMask;
    private View mInfoField;
    private Context mContext;
    /* Zype, Evgeny Cherkasov */
    private AppConfiguration appConf;
    private Drawable imageLocked;
    private Drawable imageUnlocked;
    private static Drawable infoFieldWithProgressBarBackground;
    private ContentBrowser contentBrowser;
    private  TextView titleText;
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {

        mContext = parent.getContext();
        /* Zype, Evgeny Cherkasov */
        contentBrowser = ContentBrowser.getInstance((Activity) mContext);
        appConf = ZypeConfiguration.readAppConfiguration(mContext);
        try {
            mDefaultCardImage = ContextCompat.getDrawable(mContext, R.drawable.movie);
            sFocusedFadeMask = ContextCompat.getDrawable(mContext, R.drawable.content_fade_focused_trance);

            /* Zype, Evgeny Cherkasov */
//            infoFieldWithProgressBarBackground = ContextCompat.getDrawable(mContext, R.drawable.content_fade_focused_progress_bar);
            imageLocked = ContextCompat.getDrawable(mContext, R.drawable.locked);
            imageUnlocked = ContextCompat.getDrawable(mContext, R.drawable.unlocked);
        }
        catch (Resources.NotFoundException e) {
            Log.e(TAG, "Could not find resource ", e);
            throw e;
        }

        ImageCardView cardView = new ImageCardView(mContext) {
            @Override
            public void setSelected(boolean selected) {

                super.setSelected(selected);

                // comment this code to remove shadow from the cards
                if (SHOW_TITLE) {
                    CardView cardView1 = this.findViewById(R.id.main_image_lt);
                    cardView1.setCardElevation(selected ? 6f : 0f);
                    cardView1.setContentPadding(selected ? 5 : 0, 0, 0, 0);
                }
                //

//                if (mInfoField != null) {
//                    mInfoField.setBackground(sFocusedFadeMask);
//                }
            }
        };
//        cardView.setInfoAreaBackground(sFocusedFadeMask);
        cardView.setFocusable(true);
        cardView.setFocusableInTouchMode(true);

        // Set the type and visibility of the info area.
        cardView.setCardType(SHOW_TITLE ? BaseCardView.CARD_TYPE_INFO_UNDER : BaseCardView.CARD_TYPE_INFO_OVER);
        cardView.setInfoVisibility(BaseCardView.CARD_REGION_VISIBLE_ALWAYS);

        /* Zype, Evgeny Cherkasov */
        // Make card size 16:9
//        int CARD_WIDTH_PX = 160;
        int CARD_WIDTH_PX = 210;
        mCardWidthDp = Helpers.convertPixelToDp(mContext, CARD_WIDTH_PX);

        int CARD_HEIGHT_PX = 118;
        mCardHeightDp = Helpers.convertPixelToDp(mContext, CARD_HEIGHT_PX);

        TextView subtitle = (TextView) cardView.findViewById(R.id.content_text);
        titleText = (TextView) cardView.findViewById(R.id.title_text);

        if (subtitle != null) {
            subtitle.setLines(2);
            subtitle.setMaxLines(2);
            //subtitle.setEllipsize(TextUtils.TruncateAt.END);
        }

        mInfoField = cardView.findViewById(R.id.info_field);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(Presenter.ViewHolder viewHolder, Object item) {

        ImageCardView cardView = (ImageCardView) viewHolder.view;

        if (item instanceof Content) {
            Content content = (Content) item;

            if (content.getCardImageUrl() != null) {

                // The word 'Title' is not logically correct in setTitleText,
                // the 'TitleText' is actually smaller text compared to 'ContentText',
                // so we are using TitleText to show subtitle and ContentText to show the
                // actual Title.


                if (SHOW_TITLE) {
                    String title = ContentHelper.getCardViewSubtitle(mContext, content);
                    if (titleText != null) {
                        if (TextUtils.isEmpty(title)) {
                            titleText.setVisibility(View.GONE);
                        }
                        else {
                            titleText.setVisibility(View.VISIBLE);
                        }
                    }
                    cardView.setTitleText(title);
                    cardView.setContentText(content.getTitle());
                }
                else {
                    cardView.setTitleText("");
                    cardView.setContentText("");
                }
                cardView.setMainImageDimensions(mCardWidthDp, mCardHeightDp);
                /* Zype, Evgeny Cherkasov */
                double playbackPercentage = content.getExtraValueAsDouble(Content.EXTRA_PLAYBACK_POSITION_PERCENTAGE);
                if (ZypeConfiguration.displayWatchedBarOnVideoThumbnails() && playbackPercentage > 0) {
                    SimpleTarget<Bitmap> bitmapTarget = new SimpleTarget<Bitmap>(mCardWidthDp, mCardHeightDp) {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            //cardView.setInfoAreaBackground(infoFieldWithProgressBarBackground);
                            Bitmap bitmap = Helpers.addProgressToThumbnail((Activity) mContext, resource, playbackPercentage, 0);
                            cardView.getMainImageView().setImageBitmap(bitmap);
                        }
                    };
                    GlideHelper.loadImageIntoSimpleTargetBitmap(
                            viewHolder.view.getContext(),
                            content.getCardImageUrl(),
                            new GlideHelper.LoggingListener<>(),
                            R.drawable.movie,
                            bitmapTarget);
                }
                else {
                    GlideHelper.loadImageIntoView(
                            cardView.getMainImageView(),
                            viewHolder.view.getContext(),
                            content.getCardImageUrl(),
                            new GlideHelper.LoggingListener<>(),
                            R.drawable.movie);
                    cardView.setInfoAreaBackground(sFocusedFadeMask);
                }

                /* Zype, Evgeny Cherkasov */
                // Display lock icon for paywalled video
                ImageView mBadgeImage = (ImageView) cardView.findViewById(R.id.extra_badge);
                if (contentBrowser.getPurchaseHelper().isVideoPaywalled(content)) {
                    if (contentBrowser.getPurchaseHelper().isVideoLocked(content)) {
                        mBadgeImage.setVisibility(View.VISIBLE);
                        mBadgeImage.setBackgroundColor(mContext.getResources().getColor(R.color.lock_color));
                        mBadgeImage.setImageResource(R.drawable.locked);
                    } else {
                        if (ZypeSettings.UNLOCK_TRANSPARENT) {
                            mBadgeImage.setVisibility(View.GONE);
                        } else {
                            mBadgeImage.setVisibility(View.VISIBLE);
                            mBadgeImage.setBackgroundColor(mContext.getResources().getColor(R.color.unlock_color));
                            mBadgeImage.setImageResource(R.drawable.unlocked);
                        }
                    }
                }
                else {
                    mBadgeImage.setVisibility(View.GONE);
                }
            }
        }
        else if (item instanceof ContentContainer) {
            ContentContainer contentContainer = (ContentContainer) item;
//            if(titleText != null) {
//                titleText.setVisibility(View.GONE);
//            }
//
//            if (SHOW_TITLE) {
//                cardView.setContentText(contentContainer.getName()+ "\n ");
//            }
//            else {
//                cardView.setContentText("");
//            }
            if (SHOW_TITLE) {
                String title = "";
                if (titleText != null) {
                    if (TextUtils.isEmpty(title)) {
                        titleText.setVisibility(View.GONE);
                    }
                    else {
                        titleText.setVisibility(View.VISIBLE);
                    }
                }
                cardView.setTitleText(title);
                cardView.setContentText(contentContainer.getName());
            }
            else {
                cardView.setTitleText("");
                cardView.setContentText("");
            }

            cardView.setMainImageDimensions(mCardWidthDp, mCardHeightDp);
            /* Zype, Evgeny Cherkasov */
            // Show image for playlist
            if (contentContainer.getExtraStringValue(Content.CARD_IMAGE_URL_FIELD_NAME) != null) {
                GlideHelper.loadImageIntoView(cardView.getMainImageView(),
                        viewHolder.view.getContext(),
                        contentContainer.getExtraStringValue(Content.CARD_IMAGE_URL_FIELD_NAME),
                        new GlideHelper.LoggingListener<>(),
                        R.drawable.movie);
//                Glide.with(viewHolder.view.getContext())
//                        .load(contentContainer.getExtraStringValue(Content.CARD_IMAGE_URL_FIELD_NAME))
//                        .listener(new GlideHelper.LoggingListener<>())
//                        .centerCrop()
//                        .error(mDefaultCardImage)
//                        .into(cardView.getMainImageView());
            }
            else {
                cardView.getMainImageView().setImageDrawable(mDefaultCardImage);
            }
            cardView.setInfoAreaBackground(sFocusedFadeMask);

        }
        /* Zype, Evgeny CHerkasov */
        else if (item instanceof Action) {
            Action action = (Action) item;
            cardView.setContentText(action.getLabel1());
            cardView.setMainImageScaleType(ImageView.ScaleType.CENTER);
            cardView.setMainImageDimensions(mCardWidthDp, mCardHeightDp);
            try {
                Drawable iconDrawable = ContextCompat.getDrawable(TenFootApp.getInstance().getApplicationContext(),
                        action.getIconResourceId());
                DrawableCompat.setTint(iconDrawable, ContextCompat.getColor(mContext, R.color.primary_text));
                cardView.setMainImage(iconDrawable);
            }
            catch (Resources.NotFoundException e) {
                Log.e(TAG, "Resource not found", e);
                throw e;
            }
        }
    }

    @Override
    public void onUnbindViewHolder(Presenter.ViewHolder viewHolder) {

        ImageCardView cardView = (ImageCardView) viewHolder.view;
        // Remove references to images so that the garbage collector can free up memory.
//        cardView.setBadgeImage(null);
//        cardView.getMainImageView().setImageDrawable(null);
    }
}

