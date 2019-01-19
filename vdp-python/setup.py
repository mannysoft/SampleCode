import os
import sys
import warnings

try:
    from setuptools import setup
except ImportError:
    from distutils.core import setup

try:
    from distutils.command.build_py import build_py_2to3 as build_py
except ImportError:
    from distutils.command.build_py import build_py

path, script = os.path.split(sys.argv[0])
os.chdir(os.path.abspath(path))

install_requires = []

if sys.version_info < (2, 6):
    warnings.warn(
        'Python 2.5 is not supported. ',
        DeprecationWarning)
    install_requires.append('requests >= 0.8.8, < 0.10.1')
    install_requires.append('ssl')
else:
    install_requires.append('requests >= 0.8.8')


with open('README.md') as f:
    long_description = f.read()

# Don't import visa module here, since deps may not be installed
sys.path.insert(0, os.path.join(os.path.dirname(__file__), 'vdp'))

# Get simplejson if we don't already have json
if sys.version_info < (3, 0):
    try:
        from util import json
    except ImportError:
        install_requires.append('simplejson')


setup(
    name='vdp',
    cmdclass={'build_py': build_py},
    version='1.0',
    description='Visa python Sample Code',
    long_description=long_description,
    author='Visa',
    author_email='mmodi@visa.com',
    url='https://github.com/visa/SampleCode',
    packages=['visa', 'visa.test'],
    package_data={'vdp': ['credentials.ini']},
    install_requires=install_requires,
    use_2to3=True,
    classifiers=[
        "Intended Audience :: Developers",
        "License :: {TODO}",
        "Operating System :: OS Independent",
        "Programming Language :: Python",
        "Programming Language :: Python :: 2",
        "Programming Language :: Python :: 2.6",
        "Programming Language :: Python :: 2.7",
        "Programming Language :: Python :: 3",
        "Programming Language :: Python :: 3.2",
        "Programming Language :: Python :: 3.3",
        "Programming Language :: Python :: 3.4",
        "Programming Language :: Python :: 3.5",
        "Programming Language :: Python :: Implementation :: PyPy",
        "Topic :: Software Development :: Libraries :: Python Modules"
    ])
