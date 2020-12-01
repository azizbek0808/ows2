package uz.com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import uz.com.hibernate.dao.auth.UserDao;
import uz.com.service.auth.IUserService;

@RestController
public class ReindexController extends ApiController<IUserService> {

    private final UserDao userDao;

    @Autowired
    public ReindexController(UserDao userDao, IUserService service) {
        super(service);
        this.userDao = userDao;
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @RequestMapping(value = API_PATH + V_1 + "/reindex/applications", method = RequestMethod.GET)
    public void applications() {
        /*FullTextSession fullTextSession = org.hibernate.search.Search.getFullTextSession(userDao.getSession());
        try {
            fullTextSession.createIndexer(Application.class)
                    .threadsToLoadObjects(1000)
                    .batchSizeToLoadObjects(200)
                    .typesToIndexInParallel(10)
                    .optimizeOnFinish(true)
                    .purgeAllOnStart(true)
                    .optimizeAfterPurge(true)
                    .startAndWait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }
}
