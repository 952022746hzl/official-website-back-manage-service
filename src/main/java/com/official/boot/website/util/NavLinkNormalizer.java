package com.official.boot.website.util;

/**
 * 导航跳转地址规范化工具。
 * <p>
 * type=3 分组目录 -> null（无跳转）；
 * type=1 外链 -> trim 后原样返回；
 * type=2 内部路由 -> 规范为绝对路径（补前导斜杠、折叠多斜杠、去末尾斜杠），保留 ?query 与 #fragment。
 */
public final class NavLinkNormalizer {

    private NavLinkNormalizer() {
    }

    public static String normalize(Integer type, String linkTo) {
        // 分组目录无跳转
        if (type != null && type == 3) {
            return null;
        }
        if (linkTo == null) {
            return null;
        }
        String value = linkTo.trim();
        if (value.isEmpty()) {
            return value;
        }
        // 外链原样返回
        if (type != null && type == 1) {
            return value;
        }
        // 纯同页锚点原样返回
        if (value.startsWith("#")) {
            return value;
        }
        // 误填的完整 URL 原样返回，避免破坏
        if (value.startsWith("http://") || value.startsWith("https://")) {
            return value;
        }

        // 拆出 #fragment
        String fragment = "";
        int hashIdx = value.indexOf('#');
        if (hashIdx >= 0) {
            fragment = value.substring(hashIdx);
            value = value.substring(0, hashIdx);
        }
        // 拆出 ?query
        String query = "";
        int qIdx = value.indexOf('?');
        if (qIdx >= 0) {
            query = value.substring(qIdx);
            value = value.substring(0, qIdx);
        }

        String path = value;
        if (!path.startsWith("/")) {
            path = "/" + path;
        }
        path = path.replaceAll("/{2,}", "/");
        if (path.length() > 1 && path.endsWith("/")) {
            path = path.substring(0, path.length() - 1);
        }

        return path + query + fragment;
    }
}
