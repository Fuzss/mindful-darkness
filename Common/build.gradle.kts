plugins {
    id("fuzs.multiloader.multiloader-convention-plugins-common")
}

dependencies {
    modCompileOnlyApi(sharedLibs.puzzleslib.common)
}

multiloader {
    mixins {
        clientMixin("Font\u0024PreparedTextBuilderMixin", "MultiPackResourceManagerMixin", "SpriteContentsMixin")
    }
}
