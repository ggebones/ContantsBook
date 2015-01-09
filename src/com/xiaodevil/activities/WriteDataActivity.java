package com.xiaodevil.activities;

import java.util.List;
import android.os.Bundle;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.xiaodevil.database.DatabaseHelper;
import com.xiaodevil.models.User;

public class WriteDataActivity extends OrmLiteBaseActivity<DatabaseHelper>{
	
	private TextView mTextView;
	private RuntimeExceptionDao<User, Integer> mUserDAO;
	User user;
	
	@Override
	protected void onCreate(Bundle savedInstanceState ){
		super.onCreate(savedInstanceState);
		//setContentView();
		
		mUserDAO = getHelper().getUserDataDao();
		
	}
	/**
     * ����ֵ����
     */
    private void insertTest()
    {
        for (int i = 0; i < 5; i++)
        {
            user = new User();
            user.setUserName("name" + i);
            user.setPhoneNumber("phoneNumber" + i);
            mUserDAO.createIfNotExists(user);
        }
    }

    /**
     * ����
     * 
     * @param user �����µ�user
     */
    private void update(User user)
    {
        mUserDAO.createOrUpdate(user);
        // mUserDAO.update(user);
    }

    /**
     * ����ָ����id �� username ɾ��һ��
     * 
     * @param id
     * @param username
     * @return ɾ���ɹ�����true ��ʧ�ܷ���false
     */
    private int delete(String username)
    {
        try
        {
            // ɾ��ָ������Ϣ������delete User where 'id' = id ;
            DeleteBuilder<User, Integer> deleteBuilder = mUserDAO.deleteBuilder();
            deleteBuilder.where().eq("username", username);

            return deleteBuilder.delete();
        }
        catch (java.sql.SQLException e)
        {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * ����id��ѯuser
     * 
     * @param id
     * @return
     */
    private User search(String username)
    {
        try
        {
            // ��ѯ��query ����ֵ��һ���б�
            // ���� select * from User where 'username' = username;
            List<User> users = mUserDAO.queryBuilder().where().eq("username", username).query();
            if (users.size() > 0)
                return users.get(0);
        }
        catch (java.sql.SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * ɾ��ȫ��
     */
    private void deleteAll()
    {
        mUserDAO.delete(queryAll());
    }

    /**
     * ��ѯ���е�
     */
    private List<User> queryAll()
    {
        List<User> users = mUserDAO.queryForAll();
        return users;
    }

    /**
     * ��ʾ���е�
     */
    private void display()
    {
        List<User> users = queryAll();
        for (User user : users)
        {
            mTextView.append(user.toString());
        }
    }
}
