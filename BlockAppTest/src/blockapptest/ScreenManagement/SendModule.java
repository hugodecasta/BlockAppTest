/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blockapptest.ScreenManagement;

/**
 *
 * @author i3mainz
 */
public interface SendModule
{
    public String[]getPorts();
    
    public boolean connect(String id);
    public boolean connect(int id);
    
    public boolean sendMessage(String message);
    public boolean sendByte(byte b);
    
    public boolean disconnect();
}
