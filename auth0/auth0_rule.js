function enrichAccessToken(user, context, callback) {
    if (!user.email || !user.email_verified) {
        return callback(null, user, context);
    }
    const namespace = "app";
    const assignedRoles = (context.authorization || {}).roles;
    let accessTokenClaims = context.accessToken || {};
    let userInfo = {};
    userInfo[`email`] = user.email;
    userInfo[`email_verified`] = user.email_verified;
    userInfo[`user_id`] = user.user_id;
    userInfo[`username`] = user.username;
    userInfo[`roles`] = assignedRoles;
    accessTokenClaims[`${namespace}_user`] = userInfo;
    context.accessToken = accessTokenClaims;
    callback(null, user, context);
}