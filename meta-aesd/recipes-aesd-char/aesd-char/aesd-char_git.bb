# Recipe created by recipetool
# This is the basis of a recipe and may need further editing in order to be fully functional.
# (Feel free to remove these comments when editing.)

# WARNING: the following LICENSE and LIC_FILES_CHKSUM values are best guesses - it is
# your responsibility to verify that the values are complete and correct.
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "gitsm://git@github.com/cu-ecen-aeld/assignments-3-and-later-kelanige.git;protocol=ssh;branch=assignment-8 \
           file://aesdchar-start-stop \
           "

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "748d3c1eea059b857587c896e699c51a44276a22"

S = "${WORKDIR}/git/aesd-char-driver"

inherit module
inherit update-rc.d
INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME = "aesdchar-start-stop"
# INITSCRIPT_PARAMS = "start 99 S ."

KERNEL_MODULE_AUTOLOAD += "aesdchar"

# Specify any options you want to pass to cmake using EXTRA_OECMAKE:
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"
EXTRA_OEMAKE:append:task-install = " -C ${STAGING_KERNEL_DIR} M=${S}"
RPROVIDES:${PN} += "kernel-module-aesdchar"
FILES:${PN} += "${INIT_D_DIR}/${INITSCRIPT_NAME} \
                ${bindir}/aesdchar_load \
                ${bindir}/aesdchar_unload \
                "

do_install:appeand () {
    # install -d ${D}${INIT_D_DIR}
    # install -m 0755 ${WORKDIR}/${INITSCRIPT_NAME} ${D}${INIT_D_DIR}/${INITSCRIPT_NAME}

    install -d ${D}${bindir}
    install -m 0755 ${S}/aesd-char-driver/aesdchar_load ${D}${bindir}/
    install -m 0755 ${S}/aesd-char-driver/aesdchar_unload ${D}${bindir}/

    install -d ${D}/lib/modules/${KERNEL_VERSION}/extra
    install -m 0755 ${S}/aesd-char-driver/aesdchar.ko ${D}/lib/modules/${KERNEL_VERSION}/extra/
}
