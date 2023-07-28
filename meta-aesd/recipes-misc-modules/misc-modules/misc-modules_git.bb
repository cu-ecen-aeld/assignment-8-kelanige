# Recipe created by recipetool
# This is the basis of a recipe and may need further editing in order to be fully functional.
# (Feel free to remove these comments when editing.)

# WARNING: the following LICENSE and LIC_FILES_CHKSUM values are best guesses - it is
# your responsibility to verify that the values are complete and correct.
#
# The following license files were not able to be identified and are
# represented as "Unknown" below, you will need to check them yourself:
#   LICENSE
LICENSE = "CLOSED"
LIC_FILES_CHKSUM = "file://LICENSE;md5=f098732a73b5f6f3430472f5b094ffdb"

SRC_URI = "git://git@github.com/cu-ecen-aeld/assignment-7-kelanige.git;protocol=ssh;branch=main \
           file://0001-Only-build-misc-modules-scull.patch \
           file://misc-modules-start-stop \
           "

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "b86c1f8dd886564d9b6498bcb0371c2098363626"

S = "${WORKDIR}/git"

inherit module
inherit update-rc.d
INITSCRIPT_NAME = "misc-modules-start-stop"
INITSCRIPT_PARAMS = "start 99 S ."

EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"
EXTRA_OEMAKE:append:task-install = " -C ${STAGING_KERNEL_DIR} M=${S}/misc-modules"

RPROVIDES:${PN} += "kernel-module-hello"
FILES:${PN} += "${INIT_D_DIR}/${INITSCRIPT_NAME} ${bindir}/module_load ${bindir}/module_unload"

do_install:append () {
    install -d ${D}${INIT_D_DIR}
    install -m 0755 ${WORKDIR}/${INITSCRIPT_NAME} ${D}${INIT_D_DIR}/${INITSCRIPT_NAME}

    install -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/git/misc-modules/module_load ${D}${bindir}/
    install -m 0755 ${WORKDIR}/git/misc-modules/module_unload ${D}${bindir}/
}
