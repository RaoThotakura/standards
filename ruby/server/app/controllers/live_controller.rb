class LiveController < ApplicationController
  def new
	@live = Live.new
  end
  def content
	@article = Article.new
	@article = params[:article]
	@live = Live.new
	render :inline => Live.to_html (@article[:title],@article[:text])
  end
end

class Live
  def initialize
  end 
  def content
  end 
  def to_html (title,text)
      '<table>'.tap do |table|
			table << %(<tr><td class="name">#{CGI.escapeHTML(title.to_s)}</td>)
            table << %(<td class="value">#{CGI.escapeHTML(text.to_s)}</td></tr>)	
			table << '</table>'
        end
  end  
end

class Article
  def initialize
  end
end