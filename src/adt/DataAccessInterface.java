/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adt;

/**
 *
 * @author User
 */
public interface DataAccessInterface<T> {
    public ListInterface<T> selectAllRecord();
    
    public T selectOneRecord(int id);
    
    public int addOneRecord(T newEntry);
    
    public int deleteRecord(int id);
    
    public int updateRecord(T anEntry);
    
    
}
