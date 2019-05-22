package be.ida.jetpack.carve.manager.pathpolicy.providers;

import org.apache.commons.codec.digest.DigestUtils;

public class BucketPathPolicyProvider implements PathPolicyProvider {

    @Override
    public String apply(String id) {
        String md5 = DigestUtils.md5Hex(id);
        return md5.substring(0, 3) + SEPERATOR + md5.substring(3, 6) + SEPERATOR + id;
    }
}
