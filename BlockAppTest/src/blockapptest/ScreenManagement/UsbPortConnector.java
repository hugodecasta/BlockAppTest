/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blockapptest.ScreenManagement;

import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;

/**
 *
 * @author i3mainz
 */
public class UsbPortConnector implements SendModule
{
    SerialPort serialPort;
    
    public UsbPortConnector()
    {
    }

    @Override
    public String[] getPorts()
    {
        return SerialPortList.getPortNames();
    }
    
    @Override
    public boolean connect(String id)
    {
        serialPort = new SerialPort(id);
        try
        {
            serialPort.openPort();
            serialPort.setParams(
                    SerialPort.BAUDRATE_9600,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }

    @Override
    public boolean connect(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isConnected()
    {
        return serialPort!=null;
    }

    @Override
    public boolean sendMessage(String message) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean sendByte(byte b)
    {
        try {
            serialPort.writeByte(b);
            return true;
        } catch (SerialPortException ex) {
            return false;
        }
    }

    @Override
    public boolean disconnect() {
        try {
            serialPort.closePort();
            serialPort = null;
            return true;
        } catch (SerialPortException ex) {
            return false;
        }
    }
}
