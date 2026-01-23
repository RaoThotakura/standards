class InfoController < ApplicationController
  def new
	render :text => '<p>Hitting infocontroller</p>'
  end
  def create
	render :text => '<p>Hitting infocontroller</p>'
  end 
  def properties
    if consider_all_requests_local || local_request?
      render :inline => Rails::Info.to_html
    else
		render :text => '<p>For security purposes, this information is only available to local requests.</p>', :status => 500
    end
  end
end

class Info
  def initialize
  end 
  def properties
  end 
end


