class ArticlesController < ApplicationController 
	def new
		@article  = Article.new
	end
	#
	# Approach I : define the action same as in the form_for action attribute
	# Let Rails automatically render the action.html.erb after instantiating the parameters into a instance variable
	#
	#def index
	#	@article = params[:article]
	#end
	#
	# Approach II : define the action same as in the form_for action attribute
	# Let Rails automatically render a different view (index.html.erb) within the same controller
	# after instantiating the parameters into a instance variable
	#	
	def create
		@article = params[:article]
		render "index"
	end
	#
	# Approach III : define the action same as in the form_for action attribute
	# Let Rails automatically render a inline view from within the same controller
	# after instantiating the parameters into a instance variables and invoking a custom HTML (toHtml) method
	#		
	def content
		@article = params[:article]
		article = Article.new
		title = @article[:title]
		text = @article[:text]
		render :inline => article.toHtml(title,text)
	end
	#
	# serving up json file
	# fetch?query=credits.json
	#
	def fetch
		# @credit = params[:query]
		
		@credit = fetch_credits_params
		render :partial => @credit
	end	
	
	private
		def fetch_credits_params
			filename = "500.html"
			# params.require(:fetch).permit(:query)
			render :partial => filename, :status => 500
		end
end

class Article
  def initialize
  end 
  def title
  end
  def text
  end
  def submit
  end
  def toHtml(title,text)
	'<table>'.tap do |table|
     table << %(<tr><td class="name">#{CGI.escapeHTML(title.to_s)}</td>)
     table << %(<td class="value">#{CGI.escapeHTML(text.to_s)}</td></tr>)
     table << '</table>'
	 end
  end 
end