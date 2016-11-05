package thegroup.snakego.interfaces;


public interface AnimateEntity {


    void followUser();
    //    public void followUser(final LatLng userLoc, final BaseEntity entity) {
    //        final LatLng current = entity.getLatlng();
    //        // to work with interpolator we need to get time
    //        final long time = SystemClock.uptimeMillis();
    //        final long chaseTime = 5;
    //        final LinearInterpolator inter = new LinearInterpolator();
    //
    //
    //                long timeGoing = SystemClock.uptimeMillis() - time;
    //                float i = inter.getInterpolation((float) timeGoing/time);
    //                double newLat = i * userLoc.latitude + (1-i) * entity.getLatlng().latitude;
    //                double newLong = i * userLoc.longitude + (1-i) * entity.getLatlng().longitude;
    //
    //                entity.updateLatLng(newLat, newLong);
    //
    //
    //    }


}
