//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.yb.hmalbumlib.extar.namegenerator;


public class HM_HashCodeFileNameGenerator implements HM_FileNameGenerator {
    public HM_HashCodeFileNameGenerator() {
    }

    public String generate(String imageUri) {
        return String.valueOf(imageUri.hashCode());
    }
}
