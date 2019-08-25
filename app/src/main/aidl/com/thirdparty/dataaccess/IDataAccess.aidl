// IDataAccess.aidl
package com.thirdparty.dataaccess;

// Declare any non-default types here with import statements
/*
*This aidl is used by NingXia app
*/
interface IDataAccess {

    String getSTBData(String dataName, String extData);

    int setSTBData(String dataName, String value, String extData);

}
