package com.danny.chat.presentation.di

import android.app.Application
import com.danny.chat.BuildConfig
import com.danny.chat.R
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FirebaseModule {
    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideDataBase(): FirebaseDatabase {
        return FirebaseDatabase.getInstance(BuildConfig.DATABASE_URL)
    }

    @Provides
    @Singleton
    fun provideGoogleOptions(app: Application): GoogleSignInOptions {
        return GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(app.applicationContext.getString(R.string.default_web_client_id))
            .requestEmail()
            .build();
    }
}