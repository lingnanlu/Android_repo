package io.github.lingnanlu.gaoxiaolian.core.helper;

import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.FollowCallback;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.SaveCallback;
import com.avos.avoscloud.SignUpCallback;

import java.util.Date;
import java.util.List;

import io.github.lingnanlu.gaoxiaolian.GaoXiaoLian;
import io.github.lingnanlu.gaoxiaolian.core.CallBack;
import io.github.lingnanlu.gaoxiaolian.model.Like;
import io.github.lingnanlu.gaoxiaolian.model.User;
import io.github.lingnanlu.gaoxiaolian.model.Visit;

/**
 * Created by rico on 5/15/2016.
 */
public class UserHelper {

    private static final String TAG = "UserHelper";

    public static void login(String name, String password, final CallBack<User> cb) {

        AVUser.logInInBackground(name, password, new LogInCallback<User>() {
            @Override
            public void done(User user, AVException e) {
                if (e == null) {
                    Log.d(TAG, "done: login success" );
                    GaoXiaoLian.setUser(user);
                    cb.onResult(user);
                } else {
                    Log.d(TAG, "done: login error " + e );
                    cb.onError(e);
                }
            }
        }, User.class);
    }

    public static void register(User user, final CallBack<Void> cb) {

        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    Log.d(TAG, "done: register success ");
                    cb.onResult(null);
                } else {
                    Log.d(TAG, "done: register error " + e);
                    cb.onError(e);
                }
            }
        });
    }

    public static void getUser(String userid, final CallBack<User> cb) {
        AVQuery<User> query = new AVQuery<>("_User");
        query.getInBackground(userid, new GetCallback<User>() {
            @Override
            public void done(User user, AVException e) {
                if (e == null) {
                    Log.d(TAG, "done: getUser success");
                    cb.onResult(user);
                } else {
                    Log.d(TAG, "done: getUser failed" + e);
                    cb.onError(e);
                }
            }
        });
    }

    public static void hasFollowed(final String userid, final CallBack<Boolean> cb) {
        //查询当前用户的关注列表
        AVQuery<User> followeeQuery = null;
        try {
            followeeQuery = GaoXiaoLian.getUser().followeeQuery(User.class);
            followeeQuery.findInBackground(new FindCallback<User>() {
                boolean hasFollowed = false;
                @Override
                public void done(List<User> followees, AVException e) {
                    if (e == null) {
                        Log.d(TAG, "done: query follow stated success " + followees);
                        if (followees != null) {
                            for (User follower : followees) {
                                if (userid.equals(follower.getObjectId())) {
                                    hasFollowed = true;
                                    break;
                                }
                            }
                        }
                        cb.onResult(hasFollowed);
                    } else {
                        Log.d(TAG, "done: query follow stated failed " + e);
                        cb.onError(e);
                    }

                }
            });
        } catch (AVException e) {
            e.printStackTrace();
        }
    }

    //访问用户
    public static void visit(String userid, final CallBack<Void> cb) {

        if (userid.equals(GaoXiaoLian.getUser().getObjectId())) {
            return;
        }

        Visit visit = new Visit();
        visit.put(Visit.FROM, GaoXiaoLian.getUser().getObjectId());
        visit.put(Visit.TO, userid);
        visit.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    Log.d(TAG, "done: visit success");
                } else {
                    Log.d(TAG, "done: visit failed");
                    cb.onError(e);
                }
            }
        });

    }

    public static void getVisitCount(String userid, final CallBack<Integer> cb) {

        AVQuery<Visit> query = AVObject.getQuery(Visit.class);
        query.whereEqualTo(Visit.TO, userid);
        query.findInBackground(new FindCallback<Visit>() {
            @Override
            public void done(List<Visit> visits, AVException e) {
                if (e == null) {
                    Log.d(TAG, "done: get visits success " + visits);
                    if (visits == null) {
                        cb.onResult(0);
                    }
                    cb.onResult(visits.size());
                } else {
                    Log.d(TAG, "done: get visits failed " + e);
                    cb.onError(e);
                }
            }
        });
    }

    public static void like(String userid, final CallBack<Void> cb) {

        if (userid.equals(GaoXiaoLian.getUser().getObjectId())) {
            Log.d(TAG, "like: userid same");
            return;
        }

        Like like = new Like();
        like.put(Like.FROM, GaoXiaoLian.getUser().getObjectId());
        like.put(Like.TO, userid);
        like.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    Log.d(TAG, "done: like success");
                    cb.onResult(null);
                } else {
                    Log.d(TAG, "done: like failed" + e);
                    cb.onError(e);
                }
            }
        });
    }

    public static void getLikeCount(String userid, final CallBack<Integer> cb) {

        AVQuery<Like> query = AVObject.getQuery(Like.class);
        query.whereEqualTo(Like.TO, userid);
        query.findInBackground(new FindCallback<Like>() {
            @Override
            public void done(List<Like> likes, AVException e) {
                if (e == null) {
                    Log.d(TAG, "done: get likes success " + likes);
                    if (likes == null) {
                        cb.onResult(0);
                    } else {
                        cb.onResult(likes.size());
                    }
                } else {
                    Log.d(TAG, "done: get likes failed " + e);
                    cb.onError(e);
                }
            }
        });
    }

    public static void follow(String userid, final CallBack<Void> cb) {

        GaoXiaoLian.getUser().followInBackground(userid, new FollowCallback() {

            @Override
            public void done(AVObject avObject, AVException e) {
                if (e == null) {
                    Log.d(TAG, "done: follow success");
                    cb.onResult(null);
                } else {
                    Log.d(TAG, "done: follow failed " + e);
                    cb.onError(e);
                }
            }

        });
    }

    public static void unFollow(String userid, final CallBack<Void> cb) {
        GaoXiaoLian.getUser().unfollowInBackground(userid, new FollowCallback() {
            @Override
            public void done(AVObject avObject, AVException e) {
                if (e == null) {
                    Log.d(TAG, "done: unfollow success");
                    cb.onResult(null);
                } else {
                    Log.d(TAG, "done: unfollow failed" + e);
                    cb.onError(e);
                }
            }
        });
    }

    //上线操作
    //1. 更新冒泡时间
    //2. 更新在线状态
    public static void online() {

        User user = GaoXiaoLian.getUser();
        Date date = new Date();
        user.put(User.BUBBLE_TIME, date);
        user.put(User.STATUS, User.STATUS_ONLINE);
        user.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    Log.d(TAG, "done: online success");
                } else {
                    Log.d(TAG, "done: online failed " + e);
                }
            }
        });

    }

    // TODO: 5/15/2016 也许叫为sign out更合适?
    public static void offline() {
//        if (online != null) {
//            online.deleteInBackground(new DeleteCallback() {
//                @Override
//                public void done(AVException e) {
//                    if (e == null) {
//                        Log.d(TAG, "done: offline success ");
//                        // TODO: 5/15/2016 这里可利用EventBus或广播机制
//                    } else {
//
//                    }
//                }
//            });
//        }
    }

    public static void getOnlineUsers(final CallBack<List<User>> cb) {
        AVQuery<User> query = AVObject.getQuery(User.class);
        query.whereNotEqualTo(AVUser.OBJECT_ID, GaoXiaoLian.getUser().getObjectId());
        query.whereEqualTo(User.STATUS, User.STATUS_ONLINE);
        query.orderByDescending(User.BUBBLE_TIME);
        query.findInBackground(new FindCallback<User>() {
            @Override
            public void done(List<User> onlines, AVException e) {
                if (e == null) {
                    Log.d(TAG, "done: get online users success " + onlines);
                    cb.onResult(onlines);
                } else {
                    Log.d(TAG, "done: get online users failed " + e);
                    cb.onError(e);
                }
            }
        });

    }

    public static void save(final CallBack<Void> cb) {

        GaoXiaoLian.getUser().saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    Log.d(TAG, "done: save  success");
                    cb.onResult(null);
                } else {
                    Log.d(TAG, "done: save failed " + e);
                }
            }
        });

    }

    public static void getFollowers(final CallBack<List<User>> cb) {

        AVQuery<User> followerQuery = null;
        try {
            followerQuery = GaoXiaoLian.getUser().followerQuery(User.class);
            followerQuery.include("follower");
            followerQuery.findInBackground(new FindCallback<User>() {
                @Override
                public void done(List<User> followers, AVException e) {
                    if (e == null) {
                        Log.d(TAG, "done: getFollowers success " + followers);
                        cb.onResult(followers);
                    } else {
                        Log.d(TAG, "done: getFollowers error");
                        cb.onError(e);
                    }
                }
            });
        } catch (AVException e) {
            e.printStackTrace();
        }

    }

    public static void getFollowees(final CallBack<List<User>> cb) {

        AVQuery<User> followeeQuery = null;
        try {
            followeeQuery = GaoXiaoLian.getUser().followeeQuery(User.class);
            followeeQuery.include("followee");
            followeeQuery.findInBackground(new FindCallback<User>() {
                @Override
                public void done(List<User> followees, AVException e) {
                    if (e == null) {
                        Log.d(TAG, "done: getFollowees success " + followees);
                        cb.onResult(followees);
                    } else {
                        Log.d(TAG, "done: getFollowees error");
                        cb.onError(e);
                    }
                }
            });
        } catch (AVException e) {
            e.printStackTrace();
        }
    }



}
