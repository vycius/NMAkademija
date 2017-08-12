#!/bin/bash

# Hack for BuddyBuild
sudo apt-get -y install ruby`ruby -e 'puts RUBY_VERSION[/\d+\.\d+/]'`-dev

bundle install
bundle exec danger