/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: C:\\Users\\LENOVO\\AndroidStudioProjects\\KnowUrMarket\\knowUrMarket\\src\\main\\aidl\\com\\example\\kym\\IService_MusicPlayer.aidl
 */
package com.example.kym;
public interface IService_MusicPlayer extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.example.kym.IService_MusicPlayer
{
private static final java.lang.String DESCRIPTOR = "com.example.kym.IService_MusicPlayer";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.example.kym.IService_MusicPlayer interface,
 * generating a proxy if needed.
 */
public static com.example.kym.IService_MusicPlayer asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.example.kym.IService_MusicPlayer))) {
return ((com.example.kym.IService_MusicPlayer)iin);
}
return new com.example.kym.IService_MusicPlayer.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_play:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.play(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_result:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.result();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.example.kym.IService_MusicPlayer
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
@Override public void play(java.lang.String x) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(x);
mRemote.transact(Stub.TRANSACTION_play, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public int result() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_result, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
}
static final int TRANSACTION_play = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_result = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
}
public void play(java.lang.String x) throws android.os.RemoteException;
public int result() throws android.os.RemoteException;
}
