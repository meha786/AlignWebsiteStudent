package org.mehaexample.asdDemo.dao.alignprivate;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.mehaexample.asdDemo.model.alignprivate.Photos;
import org.mehaexample.asdDemo.model.alignprivate.Privacies;

import java.util.List;

public class PhotosDao {
  private SessionFactory factory;
  private PrivaciesDao privaciesDao;

  /**
   * Default Constructor.
   */
  public PhotosDao() {
    privaciesDao = new PrivaciesDao();
    this.factory = StudentSessionFactory.getFactory();
  }

  public PhotosDao(boolean test) {
    if (test) {
      privaciesDao = new PrivaciesDao(true);
      this.factory = StudentTestSessionFactory.getFactory();
    }
  }

  /**
   * Get photo by neu id
   *
   * @param neuId Student neu id
   * @return a photo for a specific student
   */
  public Photos getPhotoByNeuId(String neuId) {
    Session session = factory.openSession();
    try {
      org.hibernate.query.Query query = session.createQuery("FROM Photos WHERE neuId = :neuId ");
      query.setParameter("neuId", neuId);
      List list = query.list();
      if (list.isEmpty()) {
        return null;
      }
      return (Photos) list.get(0);
    } finally {
      session.close();
    }
  }

  /**
   * Get a photo with privacy control.
   * If user choose not to show photo to others, it will return null.
   * @param neuId Student neu Id
   * @return A photo object
   */
  public Photos getPhotoWithPrivacy(String neuId) {
    Privacies privacy = privaciesDao.getPrivacyByNeuId(neuId);
    if (!privacy.isPhoto()) {
      return null;
    } else {
      return getPhotoByNeuId(neuId);
    }
  }

  /**
   * Update photo record for a student.
   *
   * @param photo an photo
   * @return true if updated successfully.
   */
  public synchronized boolean updatePhoto(Photos photo) {
    Transaction tx = null;

    if (!ifNuidExists(photo.getNeuId())) {
      throw new HibernateException("Photo doesn't exist.");
    }

    Session session = factory.openSession();
    try {
      tx = session.beginTransaction();
      session.saveOrUpdate(photo);
      tx.commit();
    } catch (HibernateException e) {
      if (tx != null) tx.rollback();
      throw new HibernateException(e);
    } finally {
      session.close();
    }

    return true;
  }


  /**
   * Check if a photo already exists in database.
   *
   * @param neuId
   * @return true if photo already exists.
   */
  public boolean ifNuidExists(String neuId) {
    boolean find = false;

    Session session = factory.openSession();
    try {
      org.hibernate.query.Query query = session.createQuery("FROM Photos WHERE neuId = :neuId");
      query.setParameter("neuId", neuId);
      List list = query.list();
      if (!list.isEmpty()) {
        find = true;
      }
    } finally {
      session.close();
    }

    return find;
  }
}
