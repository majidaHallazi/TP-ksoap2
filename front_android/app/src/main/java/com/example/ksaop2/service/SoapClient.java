package com.example.ksaop2.service;

import android.util.Log; // Don't forget to import Log
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.Vector;

public class SoapClient {

    private static final String NAMESPACE = "http://ws.demo.example.com/";
    private static final String URL = "http://10.0.2.2:8084/services/ws";

    private OkHttpClient client;

    public SoapClient() {
        // Setup HttpLoggingInterceptor (for OkHttp client)
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY); // Logs the request/response body

        // Create OkHttpClient and add interceptor
        client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
    }

    public interface SoapResponse<T> {
        void onSuccess(T response);
        void onError(Exception exception);
    }

    // Method to fetch compte by ID
    public void getCompteById(long id, SoapResponse<SoapObject> response) {
        new Thread(() -> {
            try {
                Log.d("SOAP_LOG_getCompteById", "Calling getCompteById with id: " + id);

                SoapObject request = new SoapObject(NAMESPACE, "getCompteById");
                request.addProperty("id", id);

                // Custom logging for request
                Log.d("SOAP_LOG_getCompteById", "Request: " + request.toString());

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.setOutputSoapObject(request);

                HttpTransportSE transport = new HttpTransportSE(URL);
                transport.call("", envelope);

                // Log the envelope to see if response is valid
                Log.d("SOAP_LOG_getCompteById", "Envelope Response: " + envelope.getResponse());

                // Custom logging for response
                SoapObject responseObject = (SoapObject) envelope.getResponse();
                Log.d("SOAP_LOG_getCompteById", "Response: " + responseObject.toString());

                response.onSuccess(responseObject);
            } catch (Exception e) {
                // Custom logging for error
                Log.e("SOAP_LOG_ERROR_getCompteById", "Error: " + e.getMessage(), e);
                response.onError(e);
            }
        }).start();
    }

    // Method to fetch all comptes
    public void getAllComptes(SoapResponse<Vector<SoapObject>> response) {
        new Thread(() -> {
            try {
                Log.d("SOAP_LOG_getAllComptes", "Calling getAllComptes");

                SoapObject request = new SoapObject(NAMESPACE, "getComptes");

                // Custom logging for request
                Log.d("SOAP_LOG_getAllComptes", "Request: " + request.toString());

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.setOutputSoapObject(request);

                HttpTransportSE transport = new HttpTransportSE(URL);
                transport.call("", envelope);

                // Custom logging for response
                Vector<SoapObject> responseObject = (Vector<SoapObject>) envelope.getResponse();
                Log.d("SOAP_LOG_getAllComptes", "Response: " + responseObject.toString());

                response.onSuccess(responseObject);
            } catch (Exception e) {
                // Custom logging for error
                Log.e("SOAP_LOG_ERROR_getAllComptes", "Error: " + e.getMessage(), e);
                response.onError(e);
            }
        }).start();
    }

    // Method to create compte
    public void createCompte(String solde, String type, SoapResponse<SoapObject> response) {
        new Thread(() -> {
            try {
                Log.d("SOAP_LOG_createCompte", "Calling createCompte with solde: " + solde + " and type: " + type);

                SoapObject request = new SoapObject(NAMESPACE, "createCompte");
                request.addProperty("solde", solde);
                request.addProperty("type", type);

                // Custom logging for request
                Log.d("SOAP_LOG_createCompte", "Request: " + request.toString());

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.setOutputSoapObject(request);

                HttpTransportSE transport = new HttpTransportSE(URL);
                transport.call("", envelope);

                // Custom logging for response
                SoapObject responseObject = (SoapObject) envelope.getResponse();
                Log.d("SOAP_LOG_createCompte", "Response: " + responseObject.toString());

                response.onSuccess(responseObject);
            } catch (Exception e) {
                // Custom logging for error
                Log.e("SOAP_LOG_ERROR_createCompte", "Error: " + e.getMessage(), e);
                response.onError(e);
            }
        }).start();
    }

    // Method to delete compte
    public void deleteCompte(long id, SoapResponse<Boolean> response) {
        new Thread(() -> {
            try {
                Log.d("SOAP_LOG_deleteCompte", "Calling deleteCompte with id: " + id);

                SoapObject request = new SoapObject(NAMESPACE, "deleteCompte");
                request.addProperty("id", id);

                // Custom logging for request
                Log.d("SOAP_LOG_deleteCompte", "Request: " + request.toString());

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.setOutputSoapObject(request);

                HttpTransportSE transport = new HttpTransportSE(URL);
                transport.call("", envelope);

                // Custom logging for response
                Boolean result = Boolean.parseBoolean(envelope.getResponse().toString());
                Log.d("SOAP_LOG_deleteCompte", "Response: " + result);

                response.onSuccess(result);
            } catch (Exception e) {
                // Custom logging for error
                Log.e("SOAP_LOG_ERROR_deleteCompte", "Error: " + e.getMessage(), e);
                response.onError(e);
            }
        }).start();
    }
}
